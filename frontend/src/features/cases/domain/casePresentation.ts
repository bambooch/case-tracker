import type { CaseSummary } from './caseSummary'

export const caseStatusOptions = ['OPEN', 'IN_REVIEW', 'CLOSED'] as const

export const caseStatusLabels: Record<(typeof caseStatusOptions)[number], string> = {
  OPEN: 'Otvoreno',
  IN_REVIEW: 'U pregledu',
  CLOSED: 'Zatvoreno',
}

export function summarizeCases(cases: CaseSummary[]) {
  return {
    total: cases.length,
    open: cases.filter((caseSummary) => caseSummary.status === 'OPEN').length,
    inReview: cases.filter((caseSummary) => caseSummary.status === 'IN_REVIEW').length,
  }
}